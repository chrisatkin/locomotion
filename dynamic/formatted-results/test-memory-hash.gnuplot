set title "hash set"
set xlabel "accesses"
set ylabel "memory usage (kb)"
set grid
set xtics nomirror
set ytics nomirror
set key top right

plot "< sort -n test-instrumentation=true-storage=HashSetTrace" using 1:($2/1024) with linespoints title "memory usage" lw 2