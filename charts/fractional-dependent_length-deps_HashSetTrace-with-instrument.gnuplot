set title "hash set, fractional dependent"
set xlabel "accesses"
set ylabel "dependencies"
set grid
set xtics nomirror
set ytics nomirror
set key top right

plot "< sort -n ../formatted-results/fractional-dependent-instrumentation=true-storage=HashSetTrace" using 1:4 with linespoints title "dependencies" ls 1