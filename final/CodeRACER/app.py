from CodeRACER import *

c = CodeRACER("192.168.4.1", 8888)

c.run("linear", "forward",0.5)
c.run("left", "forward", 1)
c.run("linear", "forward", 1.5)
