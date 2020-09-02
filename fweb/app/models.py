from datetime import datetime
import json


class Message(object):
    def __init__(self, message):
        self.message = message
        self.datetime = datetime.now().timestamp()

    def get_message(self):
        return self.message

    def set_message(self, message):
        self.message = message

    def __str__(self):
        value = {"message": self.message, "enqueued_at": self.datetime}
        return json.dumps(value)

