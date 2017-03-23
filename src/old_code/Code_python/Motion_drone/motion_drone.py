

#Roll is making the drone fly sidewards, either to left ("+") or right ("-")
def add_roll(trame_to_send):
    trame_to_send[0].append("+")    #left
def minus_roll(trame_to_send):
    trame_to_send[0].append("-")    #right

#Pitch is the movement either forward ("+") and backward ("-")
def add_pitch(trame_to_send):
    trame_to_send[1].append("+")    #forward
def minus_pitch(trame_to_send):
    trame_to_send[1].append("-")    #backward

#Yaw is the rotation either to left ("+") or right ("-")
def add_yaw(trame_to_send):
    trame_to_send[2].append("+")    #left
def minus_yaw(trame_to_send):
    trame_to_send[2].append("-")    #right


def add_throttle(trame_to_send):
    trame_to_send[3].append("+")
def minus_throttle(trame_to_send):
    trame_to_send[3].append("-")


def add_AUX1(trame_to_send):
    trame_to_send[4].append("+")
def minus_AUX1(trame_to_send):
    trame_to_send[4].append("-")


def add_AUX2(trame_to_send):
    trame_to_send[5].append("+")
def minus_AUX2(trame_to_send):
    trame_to_send[5].append("-")

#failsafe is needed in case of loss of communication between the Raspberry Pi and the drone
def add_failsafe(trame_to_send):
    trame_to_send[6].append("+")
def minus_failsafe(trame_to_send):
    trame_to_send[6].append("-")


def rectify_angle(deviation):
    '''it takes in argument the angle 'deviation'
    it changes the direction of the drone to ensure that it follows the line
    return 0 if it worked, 1 if it failed'''
    if (deviation > 0):
        add_yaw(trame_to_send)      # TODO: verify with tests if the drone rotates in the good direction
        return (0)
    elif (deviation < 0):
        minus_yaw(trame_to_send)
        return (0)
    return (1)


def rectify_offset(offset):
    '''it takes in argument the offset
    return 0 if it worked, 1 if it failed'''
    if (offset > 0):     # TODO: verify with tests if the drone rotates in the good direction
        add_roll(trame_to_send)
        return (0)
    elif (offset < 0):
        minus_roll(trame_to_send)
        return (0)
    return (1)
