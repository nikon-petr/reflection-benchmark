### Results (OpenJDK 17.0.2)
```
Benchmark                              Mode  Cnt   Score   Error  Units
BenchmarkRunner._1_code_generation     avgt   25  17.762 ± 0.337  ns/op
BenchmarkRunner._2_hard_code           avgt   25  15.652 ± 0.222  ns/op
BenchmarkRunner._3_lambda_metafactory  avgt   25  15.998 ± 0.505  ns/op
```

### Results (OpenJDK 18.0.1.1)
```
BenchmarkRunner._1_code_generation     avgt   25  16.931 ± 0.558  ns/op
BenchmarkRunner._2_hard_code           avgt   25  18.907 ± 0.945  ns/op
BenchmarkRunner._3_lambda_metafactory  avgt   25  17.937 ± 0.573  ns/op
```

### Results (openj9-0.21.0)
```
BenchmarkRunner._1_code_generation     avgt   25  52.979 ± 3.488  ns/op
BenchmarkRunner._2_hard_code           avgt   25  58.706 ± 2.835  ns/op
BenchmarkRunner._3_lambda_metafactory  avgt   25  57.826 ± 2.009  ns/op
```