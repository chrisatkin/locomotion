set terminal pdf
set output 'dissertation-gnuplottex-fig8.pdf'
set multiplot layout 1,2
load '../dynamic/formatted-results/vector-survey-all/time.gnuplot'

load '../dynamic/formatted-results/multiples-all/bloomfilter-time.gnuplot'
unset multiplot
