class Tea:
    def __init__(self, name: str, time: int, temperature: int):
        self.name: str = name
        self.time: int = time
        self.temperature: int = temperature

    def get_minutes_seconds(self) -> str:
        minutes, seconds = divmod(self.time, 60)
        return f"{minutes:02}:{seconds:02}"

    def __str__(self):
        return f"{self.name} {self.get_minutes_seconds()} {self.temperature}ºC"
