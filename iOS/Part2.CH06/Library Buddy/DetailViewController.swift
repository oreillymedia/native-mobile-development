//
//  DetailViewController.swift
//  Library Buddy
//
//  Created by Shaun Lewis on 7/11/19.
//  Copyright © 2019 Shaun Lewis. All rights reserved.
//

import UIKit

class DetailViewController: UIViewController {
    var book: Book?
    @IBOutlet var titleLabel: UILabel!
    @IBOutlet var authorsLabel: UILabel!
    @IBOutlet var isbnLabel: UILabel!
    @IBOutlet var pageCountLabel: UILabel!
    @IBOutlet var fictionLabel: UILabel!

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        if let book = book {
            populate(from: book)
        }
    }

    private func populate(from book: Book) {
        titleLabel.text = book.title

        // Flatten our authors array to a string separated by commas
        authorsLabel.text = book.authors.joined(separator: ", ")

        isbnLabel.text = book.isbn
        pageCountLabel.text = book.pageCount.description

        // Use our Bool value to display what kind of book it is
        fictionLabel.text = book.fiction ? "Fiction" : "Nonfiction"
    }

    @IBAction func saveBook(_ sender: Any) {
    }
}


@IBDesignable class SomeView: UIView {
    @IBInspectable var buttonText: String = "Click Me!" {
        didSet {
            button.titleLabel?.text = self.buttonText
        }
    }
    lazy var button: UIButton = {
        let button = UIButton(type: .custom)
        addSubview(button)
        return button
    }()

    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        setupView()
    }

    override init(frame: CGRect) {
        super.init(frame: frame)
        setupView()
    }

    convenience init(frame: CGRect, buttonText: String) {
        self.init(frame: frame)
        self.buttonText = buttonText
    }

    private func setupView() {
        backgroundColor = .red
        button.titleLabel?.text = buttonText
    }
}
