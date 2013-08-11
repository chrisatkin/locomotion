set terminal pdf
set output 'dissertation-gnuplottex-fig16.pdf'
set multiplot layout 1,2
load '../dynamic/formatted-results/long-bitvector-all/time.gnuplot'
load '../dynamic/formatted-results/long-bitvector-all/time-noinstr.gnuplot'
unset multiplot
