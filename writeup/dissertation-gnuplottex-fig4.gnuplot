set terminal pdf
set output 'dissertation-gnuplottex-fig4.pdf'
set multiplot layout 1,2
load '../dynamic/formatted-results/vector-survey-all/bloomfilter-deps.gnuplot'
load '../dynamic/formatted-results/vector-survey-none/bloomfilter-deps.gnuplot'
unset multiplot
