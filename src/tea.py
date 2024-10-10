class Tea:
    def __init__(self, name: str, minutes: int, seconds: int, temperature: int):
        self.name: str = name
        self.minutes = minutes
        self.seconds = seconds
        self.time = minutes * 60 + seconds
        self.temperature: int = temperature

    def get_minutes_seconds(self) -> str:
        return f"{self.minutes:02}:{self.seconds:02}"

    def __str__(self):
        return f"{self.name} {self.get_minutes_seconds()} {self.temperature}ºC"
