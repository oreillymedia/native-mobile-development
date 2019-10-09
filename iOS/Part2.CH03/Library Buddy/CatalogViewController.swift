//
//  CatalogViewController.swift
//  Library Buddy
//
//  Created by Shaun Lewis on 6/28/19.
//  Copyright © 2019 Shaun Lewis. All rights reserved.
//

import UIKit

class CatalogViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
extension CatalogViewController: UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return Book.sampleData.count
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        // Dequeue a table view cell
        let cell = tableView.dequeueReusableCell(withIdentifier: "CatalogTableViewCell", for: indexPath)

        // Find the correct book based on the row being populated
        let book = Book.sampleData[indexPath.row]

        // Populate the table view cell title label with the book title
        cell.textLabel?.text = book.title

        return cell
    }

}
