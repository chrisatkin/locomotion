set title "memory overhead"
set xlabel "length"
set ylabel "memory usage (KB)"
set grid
plot "../formatted-results/all-dependent" using 1:($2/1024) title "all" with lines, \
"../formatted-results/none-dependent" using 1:($2/1024) title "none" with lines