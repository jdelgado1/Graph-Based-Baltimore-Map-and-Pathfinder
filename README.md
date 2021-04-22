# Graph-Based-Baltimore-Map-and-Pathfinder
Graph implementation of map of Baltimore and implementing graph-based search algorithms, such as Dijkstra’s algorithm, to increase pathfinding accuracy. 

Tests and implements graph data strucuture with an awesome GPS-like application. Implements the graph interface using the incidence list representation. This representation is very memory-efficient for sparse graphs. You will be touring the streets of Baltimore to find the shortest route from the Johns Hopkins University campus to other destinations around Baltimore. Implements Dijkstra’s algorithm for weighted shortest paths.

Utilizes a street map of Baltimore around the JHU campus. The original data was provided by the City of Baltimore with simplified formatting to make it easier to parse. GPS coordinates and street names are real.

The street map data is available in the file baltimore.streets.txt. Here are the first few lines of the file:

-76.6254,39.3373 -76.6255,39.3373 21.5510 39256:W_UNIVERSITY_PKWY
-76.6443,39.3096 -76.6443,39.3099 133.363 1530:1800_BLK_N_WOODYEAR_ST
-76.6177,39.3293 -76.6175,39.3292 70.8768 38350:E_34TH_ST
-76.6457,39.3149 -76.6454,39.3151 120.759 41431:AVALON_AVE
-76.6568,39.3244 -76.6569,39.3243 44.6691 37858:WAHOTAN_AVE
-76.6071,39.3115 -76.6061,39.3116 285.046 31155:800_BLK_E_NORTH_AVE

Each line in the file defines a road segment. The first pair of numbers represents the GPS coordinates (longitude and latitude) of the beginning of the road segment. The second pair of numbers is end of the road segment. The next number is the length of the road segment in meters. The final field is the name of the road segment. All of the road segment names start with a unique integer index (the same index that is used on the City of Baltimore website), although not every road segment has an english name.

This is an edge-oriented representation of the street network graph: nodes in the graph are intersections, and the edges represent the road segments that connect the intersections. Some of the road segments form deadends, especially alleys that don’t connect to other roads. GPS coordinates are used to define the start and end of each road segment.
