set terminal pdf
set output 'dissertation-gnuplottex-fig4.pdf'
set multiplot layout 1,2
load '../charts/all-dependent_length-memory_HashSetTrace-with-instrument.gnuplot'
load '../charts/all-dependent_size-memory_BloomFilterTrace-with-instrument.gnuplot'
unset multiplot
