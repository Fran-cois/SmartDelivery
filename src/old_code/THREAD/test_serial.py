#!/usr/bin/env python
# -*- coding: utf-8 -*-

import time
import serial
import struct
import threading
from math import ceil

SPort = '/dev/ttyUSB0'
BRate = 115200

def Trame(L):
    M=[]
    for i,v in enumerate(L):
        sh,sl=CreationBytes(i,v)
        M.append(sh)
        M.append(sl)
    return M

def CreationBytes(id, val):
    s=(id<<10|(val & 0x3FF)) & 0xFFFF
    sl=s & 0xFF
    sh=(s>>8)&0xFF
    return sh,sl

ser = serial.Serial(
    port=SPort,
    baudrate=BRate,
    parity=serial.PARITY_NONE,
    stopbits=serial.STOPBITS_ONE,
    bytesize=serial.EIGHTBITS,
)

# 22ms 1024 DSM2
Mode  = 0x1
FadeCnt = 0
Header= [Mode, FadeCnt]

NumPacket = 8

Delay = 22e-3
# A pecket is 2 bytes
TransDelay = 2*NumPacket*(10.0/BRate)
# sleep precision is ms
WaitDelay = ceil((Delay - TransDelay)*1000)/1000.0

TEST=[512,600,400,800,0,0,0]

assert len(TEST) == NumPacket-1, "Wrong Packet Number"

print Delay, TransDelay, WaitDelay

while True:
    H = Header + Trame(TEST)
    x = bytearray(H)
    ser.write(x)
    time.sleep(WaitDelay)
ser.close()
