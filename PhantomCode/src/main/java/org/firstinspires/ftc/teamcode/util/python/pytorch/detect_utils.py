import torchvision.transforms as transforms
import cv2
import numpy as np
import torch

from coco_names import COCO_INSTANCE_CATEGORY_NAMES as coco_names

# random colour for each class ig
COLORS = np.random.uniform(0, 255, size=(len(coco_names), 3))

# define the torchvision transforms
cv2.transform = transforms.Compose([
    transforms.ToTensor(),
])