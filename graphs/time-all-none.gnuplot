set title "time overhead"
set xlabel "length"
set ylabel "memory usage (KB)"
set grid
plot "< sort -n ../formatted-results/all-dependent-time" using 1:($2/1024) title "all" with lines, \
"< sort -n ../formatted-results/none-dependent-time" using 1:($2/1024) title "none" with lines