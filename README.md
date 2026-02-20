Created by: Victor Tang, Lily Hoopes, and Christiannel Maningat
Team Assignment: Defend Your Code!

This is a program written in Java that demonstrates input validation, error handling, password hashing, file validation, and defensive programming. 
This program takes in user input, validates it by our rules that we have established, verifies a password securely using salted hashing, and writes results to an output file. 
The program is designed to never crash silently, where all invalid input is handled, and any unexpected errors are recorded in an error log.

The features that we have implemented are:
1. Input Validation:
- First and last names must:
- Must be between 1–50 characters
- Can only contain letters
- Must start with an uppercase letter

Integers must:
- Fit within a signed 4-byte integer range
- Reject non-numeric input

File names must:
- End in .txt
- Have a maximum of 30 characters
- Can consist of only letters, numbers, underscores, or hyphens
- Cannot begin or end with a hyphen
- Cannot be a reserved Windows filename
- Cannot include control characters
- The input file must exist in the current directory

2. Password Security
The program enforces strong password rules:
- Must be between 8–128 characters
- Have at least one uppercase and lowercase letter, one number, and one special character

Passwords are secured using:
- Random 16-byte salt
- SHA-256 hashing
- Base64 encoding for storage
- The user must re-enter the password for verification before the program continues.

3. File Processing
- Copies the contents of the input file into the output file
- Writes validated user inputs
- Safely computes the sum and the product using long arithmetic (to avoid overflow)

4. Error Handling and Logging
- The program never terminates unexpectedly because of user input.

If invalid input is entered:
- The user is re-prompted to enter another input again
- The error is recorded in the error_log.txt

If an unexpected exception occurs:
- The program will continue running
- The error & timestamp are logged

The log file contains:
- Error description, timestamps, and exception details (if there are any available)

5. Defensive Programming Techniques Used
- Regex input validation
- Range checking for numeric values
- Secure password hashing with salt
- Try-catch blocks around all risky operations
- Safe file existence verification
- Overflow-safe arithmetic 
- Logging of both expected and unexpected failures

How to compile and run:
Within the termninal: 
- Compile
- javac Main.java
- Run
- java Main

- Ensure any input .txt files are located in the same directory as the program.
- Files Created by the Program
- password.txt – stores salted hashed password
- error_log.txt – stores logged errors
- User-specified output file – stores final results and copies input contents
