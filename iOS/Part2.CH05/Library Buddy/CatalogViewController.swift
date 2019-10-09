//
//  CatalogViewController.swift
//  Library Buddy
//
//  Created by Shaun Lewis on 6/28/19.
//  Copyright © 2019 Shaun Lewis. All rights reserved.
//

import UIKit

class CatalogViewController: UIViewController {

    @IBOutlet weak var tableView: UITableView!
    lazy var dataSource: ListDataSource = {
        let listDataSource = ListDataSource()
        let dataController = (UIApplication.shared.delegate as? AppDelegate)!.dataController!
        listDataSource.fetchCatalogResults(with: dataController, delegate: nil)
        return listDataSource
    }()

    override func viewDidLoad() {
        super.viewDidLoad()
        tableView.dataSource = dataSource
    }

    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        if let indexPath = tableView.indexPathForSelectedRow {
            tableView.deselectRow(at: indexPath, animated: true)
        }
    }

    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if let detailViewController = segue.destination as? DetailViewController,
           let indexPath = tableView.indexPathForSelectedRow {
            detailViewController.book = dataSource.book(for: indexPath)
        }
    }
}
