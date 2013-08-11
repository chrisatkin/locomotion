set terminal pdf
set output 'dissertation-gnuplottex-fig2.pdf'
set multiplot layout 1,2
load '../dynamic/formatted-results/vector-survey-all/hash-deps.gnuplot'

load '../dynamic/formatted-results/vector-survey-all/bloomfilter-deps.gnuplot'
unset multiplot
