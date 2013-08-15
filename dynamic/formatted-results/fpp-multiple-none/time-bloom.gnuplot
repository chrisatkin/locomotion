set title "none dependent"
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

plot "< sort -k1,1 -n -k3,3 -n ../dynamic/formatted-results/fpp-multiple-none/0.05/instrumentation=true-storage=BloomFilterTrace" using ($1/1000):($5/1000000000) with linespoints title "fpp = 0.05" ls 1 lw 4,\
	"< sort -k1,1 -n -k3,3 -n ../dynamic/formatted-results/fpp-multiple-none/0.07/instrumentation=true-storage=BloomFilterTrace" using ($1/1000):($5/1000000000) with linespoints title "fpp = 0.07" ls 2 lw 4,\
	"< sort -k1,1 -n -k3,3 -n ../dynamic/formatted-results/fpp-multiple-none/0.13/instrumentation=true-storage=BloomFilterTrace" using ($1/1000):($5/1000000000) with linespoints title "fpp = 0.13" ls 3 lw 4,\
	"< sort -k1,1 -n -k3,3 -n ../dynamic/formatted-results/fpp-multiple-none/0.26/instrumentation=true-storage=BloomFilterTrace" using ($1/1000):($5/1000000000) with linespoints title "fpp = 0.26" ls 4 lw 4