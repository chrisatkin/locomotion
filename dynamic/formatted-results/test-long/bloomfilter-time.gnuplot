set title "all dependent"
set xlabel "accesses (thousands)"
set ylabel "execution time (seconds)"

#set logscale y

# grid style
set style line 80 lt rgb "#808080"
set style line 81 lt 0  # dashed
set style line 81 lt rgb "#808080"  # grey
set grid back linestyle 81
set border 3 back linestyle 80
set xtics nomirror
set ytics nomirror

factor(x) = x

set key top left

plot "< sort -u -k1,1 -n -k3,3 -n test-long-instrumentation=true-storage=BloomFilterTrace | awk '$1 * 1 == $3'" using ($1/1000):($5/1) with linespoints title "factor = 10" ls 1 lw 4,\
"< sort -n test-long-instrumentation=true-storage=HashSetTrace" using ($1/1000):(factor($3)/1) with linespoints title "hash set" ls 11 lw 4