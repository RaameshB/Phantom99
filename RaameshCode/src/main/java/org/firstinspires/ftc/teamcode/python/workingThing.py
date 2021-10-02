import tensorflow as tf
import numpy as np
import pandas as pd

from sklearn.model_selection import train_test_split
from tensorflow import keras
from keras import layers
from tensorflow.keras.layers.experimental import preprocessing

error = np.array([0])
correction = np.array([0])

# print.shape)

inputs = keras.Input(shape=error.shape, dtype=tf.double)


# ouputs = tf.convert_to_tensor(data[1], tf.double)

# model = keras.Model(inputs, )

layerStack = layers.Dense(5, 'relu')
layerStack = layerStack(inputs)
layerStack = layers.Dense(3, 'relu')(layerStack)
outputs = layers.Dense(1)(layerStack)

# outputs = layerStack

model = keras.Model(inputs, outputs, name="test")

# model.compile('sgd', 'root_mean_squared_error')

# model = model.fit()

model.summary()