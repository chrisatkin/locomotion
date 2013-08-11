set terminal pdf
set output 'dissertation-gnuplottex-fig10.pdf'
set multiplot layout 1,2
load '../dynamic/formatted-results/vector-survey-0.4/hash-time.gnuplot'

load '../dynamic/formatted-results/vector-survey-0.4/bloomfilter-time.gnuplot'
