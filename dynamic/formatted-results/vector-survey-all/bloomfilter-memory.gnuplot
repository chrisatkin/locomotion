set title "all dependent, bloom filter"
set xlabel "number of accesses (thousands)"
set ylabel "memory usage (kb)"

# grid style
set style line 80 lt rgb "#808080"
set style line 81 lt 0  # dashed
set style line 81 lt rgb "#808080"  # grey
set grid back linestyle 81
set border 3 back linestyle 80
set xtics nomirror
set ytics nomirror

set key bottom right

plot "< sort -k1,1 -n -k3,3 -n ../dynamic/formatted-results/vector-survey-all/instrumentation=true-storage=BloomFilterTrace | awk '$3 == 100000'" using ($1/1000):($2/1024) with linespoints title "expected insertions = 100000" ls 1 lw 4,\
	"< sort -k1,1 -n -k3,3 -n ../dynamic/formatted-results/vector-survey-all/instrumentation=true-storage=BloomFilterTrace | awk '$3 == 200000'" using ($1/1000):($2/1024) with linespoints title "expected insertions = 200000" ls 2 lw 4,\
	"< sort -k1,1 -n -k3,3 -n ../dynamic/formatted-results/vector-survey-all/instrumentation=true-storage=BloomFilterTrace | awk '$3 == 300000'" using ($1/1000):($2/1024) with linespoints title "expected insertions = 300000" ls 3 lw 4,\
	"< sort -k1,1 -n -k3,3 -n ../dynamic/formatted-results/vector-survey-all/instrumentation=true-storage=BloomFilterTrace | awk '$3 == 400000'" using ($1/1000):($2/1024) with linespoints title "expected insertions = 400000" ls 4 lw 4,\
	"< sort -k1,1 -n -k3,3 -n ../dynamic/formatted-results/vector-survey-all/instrumentation=true-storage=BloomFilterTrace | awk '$3 == 500000'" using ($1/1000):($2/1024) with linespoints title "expected insertions = 500000" ls 5 lw 4,\
	"< sort -k1,1 -n -k3,3 -n ../dynamic/formatted-results/vector-survey-all/instrumentation=true-storage=BloomFilterTrace | awk '$3 == 600000'" using ($1/1000):($2/1024) with linespoints title "expected insertions = 600000" ls 6 lw 4,\
	"< sort -k1,1 -n -k3,3 -n ../dynamic/formatted-results/vector-survey-all/instrumentation=true-storage=BloomFilterTrace | awk '$3 == 700000'" using ($1/1000):($2/1024) with linespoints title "expected insertions = 700000" ls 7 lw 4,\
	"< sort -k1,1 -n -k3,3 -n ../dynamic/formatted-results/vector-survey-all/instrumentation=true-storage=BloomFilterTrace | awk '$3 == 800000'" using ($1/1000):($2/1024) with linespoints title "expected insertions = 800000" ls 8 lw 4,\
	"< sort -k1,1 -n -k3,3 -n ../dynamic/formatted-results/vector-survey-all/instrumentation=true-storage=BloomFilterTrace | awk '$3 == 900000'" using ($1/1000):($2/1024) with linespoints title "expected insertions = 900000" ls 9 lw 4,\
	"< sort -k1,1 -n -k3,3 -n ../dynamic/formatted-results/vector-survey-all/instrumentation=true-storage=BloomFilterTrace | awk '$3 == 1000000'" using ($1/1000):($2/1024) with linespoints title "expected insertions = 1000000" ls 10 lw 4