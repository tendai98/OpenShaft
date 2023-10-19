from time import time, sleep
from socket import socket, AF_INET, SOCK_DGRAM

class CodeRACER:

	__addr = None
	__sock = None
	__ignition = False
	__turner_cmds = {"LEFT":"A", "RIGHT":"B", "LINEAR": "D"}
	__drive_cmds = { "FORWARD":"5", "REVERSE":"2", "STOP": "4"}

	def __init__(self, ip="192.168.4.1", port=8888):
		self.__addr = (ip , port)
		self.__sock = socket(AF_INET, SOCK_DGRAM)
		self.__ignition = True

	def map_turner(self, map):
		try:
			self.__turner_cmds.update(map)
		except:
			print("[!] Turner Mapping Error")

	def map_drive(self, map):
		try:
			self.__drive_cmds.update(map)
		except:
			print("[!] Drive Mapping Error")

	def hold(self, timeout=0.5):
		sleep(timeout)


	def __stop(self, cmd=b"D4"):
		try:
			self.__sock.sendto(cmd, self.__addr)
		except Exception as e:
			print("[-] Execution Error: {}".format(e))


	def run(self, turner_cmd="LINEAR",  drive_cmd="STOP", timeout=0.1):
		if self.__ignition:
			try:
				command = self.__turner_cmds[turner_cmd.upper()]
				command += self.__drive_cmds[drive_cmd.upper()]
				mark = time()

				while (timeout > (time() - mark)):
					try:
						self.__sock.sendto(command.encode(), self.__addr)
					except Exception as e:
						print("[-] Execution Error: {}".format(e))

				self.__stop()

			except KeyError:
				print("[!] Invalid Drive Command")

			except Exception as e:
				print("[-] Execution Error: {}".format(e))

		else:
			print("[!] Not Ready")
