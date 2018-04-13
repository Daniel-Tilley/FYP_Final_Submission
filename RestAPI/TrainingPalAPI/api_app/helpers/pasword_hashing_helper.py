from argon2 import PasswordHasher
from argon2.exceptions import VerifyMismatchError

from api_app import application
from api_app.constants.password_hashing_constants import PasswordHashingConstants


class PasswordHashingHelper:
    def __init__(self):
        pass

    @staticmethod
    def GetEncryptedHash(password):

        ph = PasswordHasher(
            memory_cost=PasswordHashingConstants.MEMORY_COST,
            time_cost=PasswordHashingConstants.TIME_COST,
            parallelism=PasswordHashingConstants.PARALLELISM,
            salt_len=PasswordHashingConstants.SALT_LEN,
            hash_len=PasswordHashingConstants.HASH_LEN
        )

        h = ph.hash(password + application.config['PEPPER'])

        try:
            parts = h.split(",")
            split_hash = parts[2].split("=")
            final_hash = split_hash[1][2:]
            return final_hash

        except:
            return None

    @staticmethod
    def VerifyEncryptedPasswordHash(incomplete_hash, password):
        ph = PasswordHasher(
            memory_cost=PasswordHashingConstants.MEMORY_COST,
            time_cost=PasswordHashingConstants.TIME_COST,
            parallelism=PasswordHashingConstants.PARALLELISM,
            salt_len=PasswordHashingConstants.SALT_LEN,
            hash_len=PasswordHashingConstants.HASH_LEN
        )

        final_hash = "$argon2i$v=19" + \
                     "$m=" + str(PasswordHashingConstants.MEMORY_COST) + \
                     ",t=" + str(PasswordHashingConstants.TIME_COST) + \
                     ",p=1$" + str(incomplete_hash)

        try:
            if ph.verify(final_hash, password + application.config['PEPPER']):
                return True

        except VerifyMismatchError:
            return None
