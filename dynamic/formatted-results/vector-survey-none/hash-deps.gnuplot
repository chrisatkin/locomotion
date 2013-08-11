set title "no dependencies, hash set"
set xlabel "number of accesses (thousands)"
set ylabel "number of dependencies"

# grid style
set style line 80 lt rgb "#808080"
set style line 81 lt 0  # dashed
set style line 81 lt rgb "#808080"  # grey
set grid back linestyle 81
set border 3 back linestyle 80
set xtics nomirror
set ytics nomirror

plot "< sort -n ../dynamic/formatted-results/vector-survey-none/instrumentation=true-storage=HashSetTrace" using ($1/1000):4 with linespoints title "hash set" ls 1 lw 4