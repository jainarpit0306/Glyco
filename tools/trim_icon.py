import os
from PIL import Image


def trim_transparency(img: Image.Image, padding: int = 8) -> Image.Image:
    # Convert to RGBA to ensure alpha channel
    if img.mode != "RGBA":
        img = img.convert("RGBA")
    bbox = img.split()[-1].getbbox()
    if not bbox:
        return img
    cropped = img.crop(bbox)
    # Add uniform padding
    w, h = cropped.size
    new = Image.new("RGBA", (w + padding * 2, h + padding * 2), (0, 0, 0, 0))
    new.paste(cropped, (padding, padding))
    return new


def process_folder(root: str):
    for dirpath, dirnames, filenames in os.walk(root):
        if os.path.basename(dirpath).startswith("mipmap-"):
            for fn in filenames:
                if fn in ("ic_launcher.png", "ic_launcher_round.png"):
                    path = os.path.join(dirpath, fn)
                    try:
                        img = Image.open(path)
                        trimmed = trim_transparency(img, padding=10)
                        trimmed.save(path, optimize=True)
                        print(f"Trimmed: {path}")
                    except Exception as e:
                        print(f"Skip {path}: {e}")


if __name__ == "__main__":
    repo_root = os.path.abspath(os.path.join(os.path.dirname(__file__), ".."))
    android_res = os.path.join(repo_root, "app", "src", "main", "res")
    process_folder(android_res)


