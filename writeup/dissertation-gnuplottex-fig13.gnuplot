set terminal pdf
set output 'dissertation-gnuplottex-fig13.pdf'
set multiplot layout 1,2
load '../dynamic/formatted-results/fpp-multiple-all/time-bloom.gnuplot'

load '../dynamic/formatted-results/fpp-multiple-none/time-bloom.gnuplot'
unset multiplot
