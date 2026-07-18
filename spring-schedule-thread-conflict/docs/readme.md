# 1. 调度器单线程知识点

## 1.1 知识点

Spring 的 `@EnableScheduling` 默认会创建一个**单线程**的 `ScheduledTaskRegistrar`（内部使用 `Executors.newSingleThreadScheduledExecutor()`）。这意味着所有 `@Scheduled` 注解的任务都共享**同一个调度线程**。

当多个定时任务配置了相同的 cron 触发时刻（如 `0/10 * * * * ?` 每10秒触发一次），执行流程是：

1. 任务 A 到达触发时刻，调度线程开始执行 A
2. 如果 A 的耗时超过了调度周期（如 A 耗时 5s），则任务 B 原本应在整10s时刻触发，但调度线程被 A 占用
3. B 只能等待 A 完成才能执行，导致 B 的实际执行时刻**偏离**了预期的整10s周期

**关键结论**：
- 默认单线程调度下，耗时任务会阻塞后续所有定时任务
- 任务的实际执行时刻 = 前序任务全部完成后的下一个可用时间点，而非原始 cron 时刻
- `fixedRate` 模式下，下次任务的触发计时是从**上次任务开始**时计算（但受单线程阻塞，仍需等待前序完成）；`cron` 模式则严格按照 cron 表达式计算下次触发时间，但由于线程被占用，实际执行会延后

## 1.2 验证思路和效果

### 验证思路

创建两个 `@Scheduled(cron = "0/10 * * * * ?")` 组件，模拟不同耗时的业务场景：

| 组件 | 任务耗时 | 说明 |
|------|---------|------|
| `EventSchedule` | 5s | 模拟较重的业务处理 |
| `OrderSchedule` | 2s | 模拟较轻的业务处理 |

两者 cron 均配置为每整10s触发一次，期望的执行时刻为 `0s、10s、20s、30s……`。

### 日志效果

```
23:23:00.007 [pool-1-thread-1] OrderSchedule   start   ← 整00s触发
23:23:02.013 [pool-1-thread-1] OrderSchedule   end
23:23:02.014 [pool-1-thread-1] EventSchedule   start   ← 被阻塞到02s才执行
23:23:07.020 [pool-1-thread-1] EventSchedule   end
23:23:10.003 [pool-1-thread-1] EventSchedule   start   ← 整10s触发
23:23:15.009 [pool-1-thread-1] EventSchedule   end
23:23:15.009 [pool-1-thread-1] OrderSchedule   start   ← 被阻塞到15s才执行！(本应在10s)
23:23:17.015 [pool-1-thread-1] OrderSchedule   end
23:23:20.005 [pool-1-thread-1] EventSchedule   start   ← 整20s触发
23:23:25.011 [pool-1-thread-1] EventSchedule   end
23:23:25.011 [pool-1-thread-1] OrderSchedule   start   ← 被阻塞到25s才执行！(本应在20s)
```

### 现象分析

- **整00s**: OrderSchedule 先触发（2s），02s 完成
- **02s~07s**: EventSchedule 接着执行（5s），07s 完成
- **整10s**: EventSchedule 再次触发（5s），15s 完成
- **15s~17s**: OrderSchedule 被延后到 15s 才执行（本应在整10s）
- **整20s**: EventSchedule 再次触发（5s），25s 完成
- **25s~27s**: OrderSchedule 被延后到 25s 才执行（本应在整20s）

**核心问题**：OrderSchedule 的 cron 触发时刻原本是 `0s、10s、20s、30s……`，但由于 EventSchedule 耗时 5s 占用了唯一的调度线程，导致 OrderSchedule 的实际执行时刻变为 `0s、15s、25s……`，完全偏离了预期的整10s周期。
