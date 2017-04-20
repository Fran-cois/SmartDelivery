#!/usr/bin/env python
# -*- coding: utf-8 -*-

import time
import serial
import struct
import threading
from math import ceil

SPort = '/dev/ttyUSB0'
BRate = 115200

DEBUT=[300,512,512,512,400,512,512]
DEBUT1=[0,512,512,512,0,512,512]
DEBUT2=[300,512,512,512,400,512,512]
DEBUT3=[0,512,512,512,0,512,512]
TEST=[300,512,512,512,400,512,512]

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
Headeroff=[0,0]
NumPacket = 8

Delay = 22e-3
# A pecket is 2 bytes
TransDelay = 2*NumPacket*(10.0/BRate)
# sleep precision is ms
WaitDelay = ceil((Delay - TransDelay)*1000)/1000.0

assert len(TEST) == NumPacket-1, "Wrong Packet Number"

print Delay, TransDelay, WaitDelay

for i in range(500):
    H = Header + Trame(DEBUT)
    x=bytearray(H)
    ser.write(x)
    time.sleep(WaitDelay)
for i in range(50):
    H= Header + Trame(DEBUT1)
    x=bytearray(H)
    ser.write(x)
    time.sleep(WaitDelay)
for i in range(0):
    H=Header + Trame(DEBUT2)
    x=bytearray(H)
    ser.write(x)
    time.sleep(WaitDelay)
for i in range(0):
    H=Header + Trame(DEBUT3)
    x=bytearray(H)
    ser.write(x)
    time.sleep(WaitDelay)
while True:
    H = Header + Trame(TEST)
    x = bytearray(H)
    ser.write(x)
    time.sleep(WaitDelay)
ser.close()
