set terminal pdf
set output 'dissertation-gnuplottex-fig7.pdf'
set multiplot layout 1,2
load '../dynamic/formatted-results/vector-survey-none/time-noinstr.gnuplot'
load '../dynamic/formatted-results/vector-survey-none/time.gnuplot'
unset multiplot
