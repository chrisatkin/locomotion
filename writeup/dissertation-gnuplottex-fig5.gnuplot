set terminal pdf
set output 'dissertation-gnuplottex-fig5.pdf'
set multiplot layout 1,2
load '../dynamic/formatted-results/multiples-all/bloomfilter-deps.gnuplot'

load '../dynamic/formatted-results/multiples-none/bloomfilter-deps.gnuplot'
unset multiplot
