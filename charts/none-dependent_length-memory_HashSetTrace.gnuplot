set title "hash set, none dependent"
set xlabel "accesses"
set ylabel "memory usage (kb)"
set grid
set xtics nomirror
set ytics nomirror
set key top right

plot "< sort -n ../formatted-results/none-dependent-instrumentation=true-storage=HashSetTrace" using 1:($2/1024) with linespoints title "dependencies" ls 1