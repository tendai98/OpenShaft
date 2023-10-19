from socket import socket, AF_INET, SOCK_DGRAM
from threading import Thread

class CodeRACER:

	__ip_addresss = None
	__port = 8888
	__sock = socket(AF_INET, SOCK_DGRAM)
	__driver_thread = None
	__COMMANDS  = "AQBQQ1Q2"
	__AUTO_DRIVER_ACTIVE = True
	__AUTO_DRIVE = True
	__ENGAGED = False
	DEBUG = False
	DRIVE = ["Q", "Q"]


	def __init__(self, ip, port):
		self.__ip_address = ip
		self.__port = port
		print("[->>] RST-RACER ADDRESS: {}:{}".format(ip, port))


	def turn_left(self):
		self.__sock.sendto(self.__COMMANDS[0:2].encode(), (self.__ip_address, self.__port))
		if self.DEBUG:
			print("[->>] TURNING LEFT")

	def turn_right(self):
		self.__sock.sendto(self.__COMMANDS[2:4].encode(), (self.__ip_address, self.__port))
		if self.DEBUG:
			print("[->>] TURNING RIGHT")

	def forward(self):
		self.__sock.sendto(self.__COMMANDS[4:6].encode(), (self.__ip_address, self.__port))
		if self.DEBUG:
			print("[->>] FORWARD DRIVE GEAR")

	def reverse(self):
		self.__sock.sendto(self.__COMMANDS[6:].encode(), (self.__ip_address, self.__port))
		if self.DEBUG:
			print("[->>] REVERSE DRIVE GEAR")


	def __auto_driver(self):
		while True:
			try:
				if(self.__ENGAGED):
					commands = self.DRIVE[0]+self.DRIVE[1]
					self.__sock.sendto(commands.encode(), (self.__ip_address, self.__port))
				if(self.__AUTO_DRIVE):
					self.__AUTO_DRIVER_ACTIVE = True
					self.__AUTO_DRIVE = False
					self.__ENGAGED = False
					break
			except:
				print("[->>] DRIVE TRANSMISSION ERROR")

	def auto_drive_on(self):
		if(self.__AUTO_DRIVER_ACTIVE):
			self.__AUTO_DRIVE = False
			self.__driver_thread = Thread(target=self.__auto_driver)
			self.__driver_thread.start()
			print("[->>] RST AUTO DRIVE: [ONLINE]")
			self.__AUTO_DRIVER_ACTIVE = False
			self.__ENGAGED = True

	def auto_drive_off(self):
		if(not self.__AUTO_DRIVER_ACTIVE):
			self.__ENGAGED = False
			self.__AUTO_DRIVE = True
			print("[->>] RST AUTO DRIVE: [OFFLINE]")

