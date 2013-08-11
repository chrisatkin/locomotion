set terminal pdf
set output 'dissertation-gnuplottex-fig15.pdf'
set multiplot layout 1,2
load '../dynamic/formatted-results/vector-survey-0.6/hash-time.gnuplot'

load '../dynamic/formatted-results/vector-survey-0.6/bloomfilter-time.gnuplot'
