set title "all dependent, bloom filter, no instrumentation, long vector"
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

plot "< sort -k1,1 -n -k3,3 -n ../dynamic/formatted-results/long-bitvector-all/instrumentation=false-storage=BloomFilterTrace | awk '$3 == 1000000'" using ($1/1000):($5/1000000000) with linespoints title "expected insertions = 100000" ls 1 lw 4,\
	"< sort -k1,1 -n -k3,3 -n ../dynamic/formatted-results/long-bitvector-all/instrumentation=false-storage=BloomFilterTrace | awk '$3 == 2000000'" using ($1/1000):($5/1000000000) with linespoints title "expected insertions = 200000" ls 2 lw 4,\
	"< sort -k1,1 -n -k3,3 -n ../dynamic/formatted-results/long-bitvector-all/instrumentation=false-storage=BloomFilterTrace | awk '$3 == 3000000'" using ($1/1000):($5/1000000000) with linespoints title "expected insertions = 300000" ls 3 lw 4,\
	"< sort -k1,1 -n -k3,3 -n ../dynamic/formatted-results/long-bitvector-all/instrumentation=false-storage=BloomFilterTrace | awk '$3 == 4000000'" using ($1/1000):($5/1000000000) with linespoints title "expected insertions = 400000" ls 4 lw 4,\
	"< sort -k1,1 -n -k3,3 -n ../dynamic/formatted-results/long-bitvector-all/instrumentation=false-storage=BloomFilterTrace | awk '$3 == 5000000'" using ($1/1000):($5/1000000000) with linespoints title "expected insertions = 500000" ls 5 lw 4,\
	"< sort -k1,1 -n -k3,3 -n ../dynamic/formatted-results/long-bitvector-all/instrumentation=false-storage=BloomFilterTrace | awk '$3 == 6000000'" using ($1/1000):($5/1000000000) with linespoints title "expected insertions = 600000" ls 6 lw 4,\
	"< sort -k1,1 -n -k3,3 -n ../dynamic/formatted-results/long-bitvector-all/instrumentation=false-storage=BloomFilterTrace | awk '$3 == 7000000'" using ($1/1000):($5/1000000000) with linespoints title "expected insertions = 700000" ls 7 lw 4,\
	"< sort -k1,1 -n -k3,3 -n ../dynamic/formatted-results/long-bitvector-all/instrumentation=false-storage=BloomFilterTrace | awk '$3 == 8000000'" using ($1/1000):($5/1000000000) with linespoints title "expected insertions = 800000" ls 8 lw 4,\
	"< sort -k1,1 -n -k3,3 -n ../dynamic/formatted-results/long-bitvector-all/instrumentation=false-storage=BloomFilterTrace | awk '$3 == 9000000'" using ($1/1000):($5/1000000000) with linespoints title "expected insertions = 900000" ls 9 lw 4,\
	"< sort -k1,1 -n -k3,3 -n ../dynamic/formatted-results/long-bitvector-all/instrumentation=false-storage=BloomFilterTrace | awk '$3 == 10000000'" using ($1/1000):($5/1000000000) with linespoints title "expected insertions = 10000000" ls 10 lw 4,\
	"< sort -n ../dynamic/formatted-results/long-bitvector-all/instrumentation=false-storage=HashSetTrace" using ($1/1000):($3/1000000000) with linespoints title "hash set" ls 11 lw 4