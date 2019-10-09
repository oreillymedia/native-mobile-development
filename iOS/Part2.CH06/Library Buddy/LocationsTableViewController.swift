//
//  LocationsTableViewController.swift
//  Library Buddy
//
//  Created by Shaun Lewis on 7/15/19.
//  Copyright © 2019 Shaun Lewis. All rights reserved.
//

import UIKit

class LocationsTableViewController: UITableViewController {

    let locationsController = LocationsController()
    var locations: [Location] = []

    override func viewDidLoad() {
        super.viewDidLoad()

        let searchController = UISearchController(searchResultsController: nil)
        searchController.searchBar.delegate = self
        searchController.searchBar.placeholder = "Search Locations by Country"
        searchController.obscuresBackgroundDuringPresentation = false
        definesPresentationContext = true

        navigationItem.searchController = searchController
        navigationItem.hidesSearchBarWhenScrolling = false
    }

    // MARK: - Table view data source
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return locations.count
    }

    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        // Dequeue a table view cell
        let cell = tableView.dequeueReusableCell(withIdentifier: "LocationCell", for: indexPath)

        // Find the correct location based on the row being populated
        let location = locations[indexPath.row]

        // Style the cell
        cell.textLabel?.text = "\(location.streetAddress)\n\(location.city), \(location.country)\nHours: \(location.hours)"
        cell.detailTextLabel?.text = location.emoji

        return cell
    }
}

extension LocationsTableViewController: UISearchBarDelegate {
    func searchBarTextDidEndEditing(_ searchBar: UISearchBar) {
        let country = searchBar.text ?? ""
        guard country != "" else {
            self.locations = []
            self.tableView.reloadData()
            return
        }
        locationsController.fetchLocations(for: country, completionHandler: { (locations) in
            DispatchQueue.main.async {
                self.locations = locations
                self.tableView.reloadData()
            }
        }) { (error) in
            DispatchQueue.main.async {
                self.locations = []
                self.tableView.reloadData()
            }
        }
    }

    func searchBarCancelButtonClicked(_ searchBar: UISearchBar) {
        self.locations = []
        self.tableView.reloadData()
    }
}
