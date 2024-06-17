import os
import sys
import random
import time
import pandas as pd

file = './hamiltonian_cycle.py'

def execute():
    exec(open(file).read())


sys.argv = ['hamiltonian_dynamic', '0', '0', '0']
for size in range(1, 16):

    # number of repeats
    sys.argv[3] = '30'

    # size of the graph
    sys.argv[1] = size

    # edge density
    sys.argv[2] = '0.3'
    execute()

    sys.argv[2] = '0.5'
    execute()

    sys.argv[2] = '0.7'
    execute()

