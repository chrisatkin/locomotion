set title "no dependencies, bloom filter"
set xlabel "number of accesses (thousands)"
set ylabel "execution time (sec)"

# grid style
set style line 80 lt rgb "#808080"
set style line 81 lt 0  # dashed
set style line 81 lt rgb "#808080"  # grey
set grid back linestyle 81
set border 3 back linestyle 80
set xtics nomirror
set ytics nomirror

set key bottom right

plot "< sort -n ../dynamic/formatted-results/vector-survey-none-new-new/instrumentation=true-storage=HashSetTrace" using ($1/1000):($3/1000000) with linespoints title "hash set default" ls 1 lw 4,\
plot "< sort -n ../dynamic/formatted-results/vector-survey-none-new-new/instrumentation=true-storage=HashSetWellConfigured" using ($1/1000):($3/1000000) with linespoints title "hash set optimal" ls 2 lw 4