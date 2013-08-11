set terminal pdf
set output 'dissertation-gnuplottex-fig3.pdf'
set multiplot layout 1,2
load '../dynamic/formatted-results/vector-survey-all/hash-memory.gnuplot'

load '../dynamic/formatted-results/vector-survey-all/bloomfilter-memory.gnuplot'
