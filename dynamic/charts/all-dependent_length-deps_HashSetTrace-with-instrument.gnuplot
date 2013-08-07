set title "hash set, all dependent"
set xlabel "accesses"
set ylabel "dependencies"
set grid
set xtics nomirror
set ytics nomirror
set key top right

plot "< sort -n ../formatted-results/all-dependent-instrumentation=true-storage=HashSetTrace" using 1:4 with linespoints title "dependencies" ls 1