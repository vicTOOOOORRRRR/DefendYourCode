import re
import os
import base64
import hashlib
import secrets
from datetime import datetime


# Error logging 
def log_error(message, exc=None):
    try:
        with open("error_log.txt", "a") as log:
            log.write(f"ERROR: {message}\n")
            log.write(f"TIME: {datetime.now()}\n")
            if exc:
                log.write(f"DETAILS: {exc}\n")
            log.write("-----------------------------------\n")
    except Exception:
        print("CRITICAL: Could not write to error log.")


# name validation
def get_valid_name(label):
    regex = r"^[A-Z][a-z]{0,49}$"
    while True:
        name = input(f"Enter your {label} name: ")
        if re.fullmatch(regex, name):
            return name
        print("ERROR: Invalid name format.")
        log_error(f"Invalid {label} name: {name}")


# integer validation 
def get_valid_int(label):
    while True:
        line = input(f"Enter {label}: ")
        try:
            val = int(line)
            if val < -2147483648 or val > 2147483647:
                raise ValueError("Out of 4-byte range")
            return val
        except Exception as e:
            print("ERROR: Invalid integer.")
            log_error(f"Invalid integer entered: {line}", e)


# file name validation
def get_valid_filename(io_type):
    regex = r"^[A-Za-z0-9_](?:[A-Za-z0-9_-]{0,24}[A-Za-z0-9_])?\.txt$"
    reserved = {
        "CON","PRN","AUX","NUL",
        "COM1","COM2","COM3","COM4","COM5","COM6","COM7","COM8","COM9",
        "LPT1","LPT2","LPT3","LPT4","LPT5","LPT6","LPT7","LPT8","LPT9"
    }

    while True:
        name = input(f"Enter {io_type} file name: ")

        if not name or len(name) > 30:
            print("ERROR: Invalid file length.")
            log_error(f"Invalid filename length: {name}")
            continue

        if any(ord(c) < 32 for c in name):
            print("ERROR: Control characters not allowed.")
            log_error(f"Control char in filename: {name}")
            continue

        if not re.fullmatch(regex, name):
            print("ERROR: File name format invalid.")
            log_error(f"Bad filename format: {name}")
            continue

        base = name[:-4].upper()
        if base in reserved:
            print("ERROR: Reserved Windows filename.")
            log_error(f"Reserved filename used: {name}")
            continue

        if io_type == "INPUT" and not os.path.isfile(name):
            print("ERROR: Input file does not exist.")
            log_error(f"Input file missing: {name}")
            continue

        return name


# password validation and hashing
def get_valid_password(prompt):
    regex = r"^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[^A-Za-z\d]).{8,128}$"
    while True:
        pwd = input(prompt)
        if re.fullmatch(regex, pwd):
            return pwd
        print("ERROR: Password does not meet requirements.")
        log_error("Weak password entered")


def hash_password(password, salt):
    return base64.b64encode(
        hashlib.sha256(salt + password.encode()).digest()
    ).decode()


def handle_password():
    while True:
        try:
            pwd = get_valid_password("Enter password: ")

            salt = secrets.token_bytes(16)
            hashed = hash_password(pwd, salt)

            with open("password.txt", "w") as f:
                f.write(base64.b64encode(salt).decode() + "\n")
                f.write(hashed)

            confirm = get_valid_password("Re-enter password: ")

            with open("password.txt") as f:
                stored_salt = base64.b64decode(f.readline().strip())
                stored_hash = f.readline().strip()

            if hash_password(confirm, stored_salt) == stored_hash:
                print("Password verified.")
                return
            else:
                print("ERROR: Passwords do not match.")
                log_error("Password mismatch")

        except Exception as e:
            print("ERROR processing password.")
            log_error("Password processing failure", e)


# output writer 
def write_output(first, last, a, b, infile, outfile):
    try:
        with open(outfile, "w") as out, open(infile) as inp:

            out.write(f"First Name: {first}\n")
            out.write(f"Last Name: {last}\n")
            out.write(f"First Integer: {a}\n")
            out.write(f"Second Integer: {b}\n")
            out.write(f"Sum: {a + b}\n")
            out.write(f"Product: {a * b}\n\n")

            out.write("----- Input File Contents -----\n")
            for line in inp:
                out.write(line)

        print("Output file written successfully.")

    except Exception as e:
        print("ERROR writing output file.")
        log_error("Output file write failed", e)


# main program loop 
def main():
    while True:
        try:
            first = get_valid_name("FIRST")
            last = get_valid_name("LAST")
            a = get_valid_int("First Integer")
            b = get_valid_int("Second Integer")
            infile = get_valid_filename("INPUT")
            outfile = get_valid_filename("OUTPUT")

            handle_password()
            write_output(first, last, a, b, infile, outfile)

            print("Program completed successfully.")
            break

        except Exception as e:
            print("Unexpected error, restarting input.")
            log_error("Unexpected program failure", e)


if __name__ == "__main__":
    main()