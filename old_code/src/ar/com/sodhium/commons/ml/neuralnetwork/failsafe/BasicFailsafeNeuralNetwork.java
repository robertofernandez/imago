package ar.com.sodhium.commons.ml.neuralnetwork.failsafe;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BasicFailsafeNeuralNetwork {
    @SerializedName("weights-matrix")
    @Expose
    private double[][] weightsInputHidden;
    private double[] weightsHiddenOutput;
    private double[] hiddenLayer;
    private double output;

    /**
     * 
     * @param inputSize
     *            Amount of training inputs.
     * @param hiddenSize
     *            Amount of elements in hidden layers.
     * @param outputSize
     *            ? TODO find out
     */
    public BasicFailsafeNeuralNetwork(int inputSize, int hiddenSize, int outputSize) {
        weightsInputHidden = new double[inputSize][hiddenSize];
        weightsHiddenOutput = new double[hiddenSize];
        hiddenLayer = new double[hiddenSize];

        // Random initialization of weights
        for (int i = 0; i < inputSize; i++) {
            for (int j = 0; j < hiddenSize; j++) {
                weightsInputHidden[i][j] = Math.random();
            }
        }

        for (int i = 0; i < hiddenSize; i++) {
            weightsHiddenOutput[i] = Math.random();
        }
    }

    public double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }

    public double[] forwardPropagation(double[] input) {
        // hidden layer values calculation
        for (int i = 0; i < hiddenLayer.length; i++) {
            double sum = 0;
            for (int j = 0; j < Math.min(input.length, weightsInputHidden.length); j++) {
                sum += input[j] * weightsInputHidden[j][i];
            }
            hiddenLayer[i] = sigmoid(sum);
        }

        // output value calculation
        double outputSum = 0;
        for (int i = 0; i < hiddenLayer.length; i++) {
            outputSum += hiddenLayer[i] * weightsHiddenOutput[i];
        }
        output = sigmoid(outputSum);

        return new double[] { output };
    }

    public void backwardPropagation(double[] input, double[] target, double learningRate) {
        double[] outputErrors = new double[1];
        double[] hiddenErrors = new double[hiddenLayer.length];

        // output error calculation
        outputErrors[0] = (target[0] - output) * output * (1 - output);

        // hidden layer error calculation
        for (int i = 0; i < hiddenLayer.length; i++) {
            double sum = 0;
            for (int j = 0; j < outputErrors.length; j++) {
                sum += outputErrors[j] * weightsHiddenOutput[i];
            }
            hiddenErrors[i] = hiddenLayer[i] * (1 - hiddenLayer[i]) * sum;
        }

        // updating weights in hidden layer
        for (int i = 0; i < hiddenLayer.length; i++) {
            weightsHiddenOutput[i] += learningRate * outputErrors[0] * hiddenLayer[i];
        }

        // updating input weights for hidden layer
        for (int i = 0; i < Math.min(input.length, weightsInputHidden.length); i++) {
            for (int j = 0; j < Math.min(hiddenLayer.length, weightsInputHidden[i].length); j++) {
                weightsInputHidden[i][j] += learningRate * hiddenErrors[j] * input[i];
            }
        }
    }

    public void train(double[][] trainingData, double[][] targetData, int epochs) {
        for (int epoch = 0; epoch < epochs; epoch++) {
            for (int i = 0; i < trainingData.length; i++) {
                double[] input = trainingData[i];
                double[] target = targetData[i];

                forwardPropagation(input);
                backwardPropagation(input, target, 0.1);
            }
        }
    }

    public double getOutput() {
        return output;
    }

}
