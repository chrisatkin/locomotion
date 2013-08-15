set title "none dependent"
set xlabel "number of accesses (thousands)"
set ylabel "number of dependencies"

#set logscale y

# grid style
set style line 80 lt rgb "#808080"
set style line 81 lt 0  # dashed
set style line 81 lt rgb "#808080"  # grey
set grid back linestyle 81
set border 3 back linestyle 80
set xtics nomirror
set ytics nomirror

set key bottom right

plot "< sort -u -k1,1 -n -k3,3 -n ../dynamic/formatted-results/multiples-none/instrumentation=true-storage=BloomFilterTrace | awk '$1 * 10 == $3'" using ($1/1000):4 with linespoints title "factor = 10" ls 2 lw 4,\
"< sort -u -k1,1 -n -k3,3 -n ../dynamic/formatted-results/multiples-none/instrumentation=true-storage=BloomFilterTrace | awk '$1 * 20 == $3'" using ($1/1000):4 with linespoints title "factor = 20" ls 3 lw 4,\
"< sort -u -k1,1 -n -k3,3 -n ../dynamic/formatted-results/multiples-none/instrumentation=true-storage=BloomFilterTrace | awk '$1 * 30 == $3'" using ($1/1000):4 with linespoints title "factor = 30" ls 4 lw 4,\
"< sort -u -k1,1 -n -k3,3 -n ../dynamic/formatted-results/multiples-none/instrumentation=true-storage=BloomFilterTrace | awk '$1 * 40 == $3'" using ($1/1000):4 with linespoints title "factor = 40" ls 5 lw 4,\
"< sort -u -k1,1 -n -k3,3 -n ../dynamic/formatted-results/multiples-none/instrumentation=true-storage=BloomFilterTrace | awk '$1 * 50 == $3'" using ($1/1000):4 with linespoints title "factor = 50" ls 6 lw 4,\
"< sort -u -k1,1 -n -k3,3 -n ../dynamic/formatted-results/multiples-none/instrumentation=true-storage=BloomFilterTrace | awk '$1 * 60 == $3'" using ($1/1000):4 with linespoints title "factor = 60" ls 7 lw 4,\
"< sort -u -k1,1 -n -k3,3 -n ../dynamic/formatted-results/multiples-none/instrumentation=true-storage=BloomFilterTrace | awk '$1 * 70 == $3'" using ($1/1000):4 with linespoints title "factor = 70" ls 8 lw 4,\
"< sort -u -k1,1 -n -k3,3 -n ../dynamic/formatted-results/multiples-none/instrumentation=true-storage=BloomFilterTrace | awk '$1 * 80 == $3'" using ($1/1000):4 with linespoints title "factor = 80" ls 9 lw 4,\
"< sort -u -k1,1 -n -k3,3 -n ../dynamic/formatted-results/multiples-none/instrumentation=true-storage=BloomFilterTrace | awk '$1 * 90 == $3'" using ($1/1000):4 with linespoints title "factor = 90" ls 10 lw 4,\
"< sort -u -k1,1 -n -k3,3 -n ../dynamic/formatted-results/multiples-none/instrumentation=true-storage=BloomFilterTrace | awk '$1 * 100 == $3'" using ($1/1000):4 with linespoints title "factor = 100" ls 11 lw 4