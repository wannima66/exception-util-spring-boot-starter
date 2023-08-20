## 项目介绍
该项目是基于springboot实现的一个工具类组件，目前主要是实现日志记录功能。

在日常开发中，日志记录非常的重要，如何优雅的记录日志以及自定义日志的处理，针对不同的异常类型做不同的处理，在排查问题时显得尤为重要。

本工具基于此目的，提供一个开箱即用的日志工具类进行便捷的记录日志。

## 计划

 - [ ] 优先！ 实现使用日志实现记录日志，有默认，项目中有则使用项目中。
 - [ ] 增加日志记录方法传参，实现更自由的自定义
 - [ ] 支持用户覆盖默认的adapter
 - [ ] 代码规范调整，其他优化点等

## 开发规范

1. 不允许强制推送到release分支，进行feature分支开发后进行merge合并。
2. 代码需要由另一个开发者进行review后才能合入主分支。
3. 提交commit规范，合并时写明改动点。

示例：  
例如：小A开发了新功能，推到远程分值`feature_A`,然后提交`merge request`，
由小B进行`approval`后进行代码的合并。并且提交代码时的commit说明了提交的功能点。   

