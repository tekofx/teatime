class Tea:
    def __init__(
        self, name: str, minutes: int, seconds: int, temperature: int, color: str
    ):
        self.name: str = name
        self.minutes: int = minutes
        self.seconds: int = seconds
        self.time_seconds: int = minutes * 60 + seconds
        self.time: str = f"{self.minutes:02}:{self.seconds:02}"
        self.temperature: int = temperature
        self.color: str = color

    def __str__(self):
        return f"{self.name} {self.time} {self.temperature}ºC"
