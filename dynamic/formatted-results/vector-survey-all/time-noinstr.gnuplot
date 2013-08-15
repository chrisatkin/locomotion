set title "all dependent, no instrumentation"
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

set key top left

plot "< sort -k1,1 -n -k3,3 -n ../dynamic/formatted-results/vector-survey-all/instrumentation=false-storage=BloomFilterTrace | awk '$3 == 100000'" using ($1/1000):($5/1000000000) with linespoints title "factor = 10" ls 1 lw 4,\
	"< sort -k1,1 -n -k3,3 -n ../dynamic/formatted-results/vector-survey-all/instrumentation=false-storage=BloomFilterTrace | awk '$3 == 200000'" using ($1/1000):($5/1000000000) with linespoints title "factor = 20" ls 2 lw 4,\
	"< sort -k1,1 -n -k3,3 -n ../dynamic/formatted-results/vector-survey-all/instrumentation=false-storage=BloomFilterTrace | awk '$3 == 300000'" using ($1/1000):($5/1000000000) with linespoints title "factor = 30" ls 3 lw 4,\
	"< sort -k1,1 -n -k3,3 -n ../dynamic/formatted-results/vector-survey-all/instrumentation=false-storage=BloomFilterTrace | awk '$3 == 400000'" using ($1/1000):($5/1000000000) with linespoints title "factor = 40" ls 4 lw 4,\
	"< sort -k1,1 -n -k3,3 -n ../dynamic/formatted-results/vector-survey-all/instrumentation=false-storage=BloomFilterTrace | awk '$3 == 500000'" using ($1/1000):($5/1000000000) with linespoints title "factor = 50" ls 5 lw 4,\
	"< sort -k1,1 -n -k3,3 -n ../dynamic/formatted-results/vector-survey-all/instrumentation=false-storage=BloomFilterTrace | awk '$3 == 600000'" using ($1/1000):($5/1000000000) with linespoints title "factor = 60" ls 6 lw 4,\
	"< sort -k1,1 -n -k3,3 -n ../dynamic/formatted-results/vector-survey-all/instrumentation=false-storage=BloomFilterTrace | awk '$3 == 700000'" using ($1/1000):($5/1000000000) with linespoints title "factor = 70" ls 7 lw 4,\
	"< sort -k1,1 -n -k3,3 -n ../dynamic/formatted-results/vector-survey-all/instrumentation=false-storage=BloomFilterTrace | awk '$3 == 800000'" using ($1/1000):($5/1000000000) with linespoints title "factor = 80" ls 8 lw 4,\
	"< sort -k1,1 -n -k3,3 -n ../dynamic/formatted-results/vector-survey-all/instrumentation=false-storage=BloomFilterTrace | awk '$3 == 900000'" using ($1/1000):($5/1000000000) with linespoints title "factor = 90" ls 9 lw 4,\
	"< sort -k1,1 -n -k3,3 -n ../dynamic/formatted-results/vector-survey-all/instrumentation=false-storage=BloomFilterTrace | awk '$3 == 1000000'" using ($1/1000):($5/1000000000) with linespoints title "factor = 100" ls 10 lw 4,\
	"< sort -n ../dynamic/formatted-results/vector-survey-all/instrumentation=false-storage=HashSetTrace" using ($1/1000):($3/1000000000) with linespoints title "hash set" ls 11 lw 4