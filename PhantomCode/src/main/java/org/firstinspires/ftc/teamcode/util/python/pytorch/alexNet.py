import torchvision.models as models

model = models.alexnet(pretrained=True)
print(model)