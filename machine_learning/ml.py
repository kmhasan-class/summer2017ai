from sklearn import tree
#features
# height
# weight
# age

#labels
# healthy
# not_healthy

features = [[33],[41],[32],[60],[65],[73],[78],[82],[90]
            ]
labels = ['F', 'D', 'F', 'B', 'B+', 'A-', 'A', 'A+', 'A+']

classifier = tree.DecisionTreeClassifier()

classifier.fit(features, labels)
result = classifier.predict([[85], [20], [50], [55], [78]])

print(result)
