## 分支含义

`no-elasticsearch` 分支的含义是不使用 elasticsearch 搜索引擎。原因如下：

服务器的内存有限，而 elasticsearch 需要大量的内存。如果使用 elasticsearch，
那么服务器的内存会不够用，因为服务器的内存为 `2G`，而 `elasticsearch` 启动
之后就消耗了将近一个 G，其他的服务也会受到影响。

> 基于该分支创建的新的提交都不应该使用 elasticsearch 搜索引擎。应在 main
> 分支中使用 elasticsearch 搜索引擎。