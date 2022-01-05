from posixpath import join
import subprocess
from os.path import split
from os import listdir, remove
from typing import Tuple


class NonZeroExitStatus (BaseException):
    def __init__(self, custom_output: str) -> None:
        self.message = custom_output

    def __str__(self) -> str:
        return "[NonZeroExitStatus Error]\n" + self.message


class DifferentCompilerOutput (Exception):
    def __init__(self, custom_output: str, javac_output: str) -> None:
        self.message = f"""
Actual: {custom_output}
Expected: {javac_output}
"""

    def __str__(self) -> str:
        return "[DifferentCompilerOutput Error]\n" + self.message


class CompilationError (Exception):
    def __init__(self, type) -> None:
        self.message = type

    def __str__(self) -> str:
        return "Compilation Error : " + self.message


def clean_output_dir(dir: str) -> None:
    """
    Delete all files from a directory.
    """

    for f in listdir(dir):
        remove(join(dir, f))


def execute_javac(program_path: str) -> str:
    """
    Return the output of the java program from javac.
    """

    _, filename = split(program_path)
    subprocess.call(["javac", program_path, "-d", join("test", "output")])
    res = subprocess.check_output(
        ["java", join(*filename.split(".")[:-1])], cwd=join("test", "output"))
    return res.decode("utf8")


def copy_file_content(source: str, destination: str) -> None:
    with open(source, "r") as f:
        data = f.read()

    with open(destination, "w") as f:
        f.write(data)


def check_if_compilation_error(compilator_output: str) -> None:
    """
    Raise an error if compilation have been abord
    """

    compilation_abord = compilator_output.split("Compilation aborted : ")
    if (len(compilation_abord) > 1):
        raise CompilationError(compilation_abord[1])


def get_compiler_output() -> str:
    """
    Execute compilation and return the result.
    """

    return subprocess.check_output(["./compile.sh"], encoding="utf8")


def parse_compiler_output(output: str) -> Tuple[str, str]:
    """
    Parse to get only the MIPS output.
    Return that part only and the exit status.

    Return: (mips_output, exit_status)
    """

    mips_header = "== Exécution Mars de input.mips ===\n"
    exit_status_header = "Exit status "

    mips_output = output.split(mips_header)[1].split(exit_status_header)[0]
    exit_status = output.split(exit_status_header)[1]

    return (mips_output, exit_status.strip())


def check_if_output_is_correct(program_path: str) -> str:
    """
    Check if the compiler output is the same as the javac output
    """

    copy_file_content(program_path, "input.txt")
    compiler_output = get_compiler_output()
    mips_output, exit_status = parse_compiler_output(compiler_output)

    if int(exit_status) != 0:
        raise NonZeroExitStatus(f"[{exit_status}] " + mips_output)

    javac_output = execute_javac(program_path)

    if mips_output != javac_output:
        raise DifferentCompilerOutput(mips_output, javac_output)

    clean_output_dir(join("test", "output"))


def test_all_files(dir: str) -> None:
    """
    Test loop
    """

    for f in listdir(dir):
        program_path = join(dir, f)
        if program_path.split(".")[-1] != "java":
            continue

        print("Testing " + program_path + " ...")
        check_if_output_is_correct(program_path=program_path)

if __name__ == "__main__":
    test_all_files(join("Exemples", "Milestone"))
    test_all_files(join("Exemples", "Modern"))
    test_all_files(join("Exemples", "Running"))

    print("✅ All tests passed")
